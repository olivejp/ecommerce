import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {CategoryService, TypeCategory} from 'app/entities/category/category.service';
import {from, Observable, of, Subscription} from 'rxjs';
import {concatMap, delay, distinct, map, tap, toArray} from 'rxjs/operators';
import {Category, ICategory} from "app/shared/model/category.model";
import {FlatTreeControl} from '@angular/cdk/tree';
import {MatTreeFlatDataSource, MatTreeFlattener} from '@angular/material/tree';
import {ConfirmationDialogService} from "app/shared/confirm/ConfirmationDialogService";
import {v4 as uuidv4} from 'uuid';

export class CategoryCompleteModel extends Category {
  public children: CategoryCompleteModel[];

  public level: number;
  public uid: string;

  constructor(category: Category, children: CategoryCompleteModel[], level = 0) {
    super(category.id, category.name, category.transiant, category.categoryParentId, category.attributs, category.attributs);
    this.children = children;
    this.level = level;
    this.uid = uuidv4();
  }
}

@Component({
  selector: 'jhi-category-dropdown',
  templateUrl: './category-dropdown.component.html',
  styleUrls: ['./category-dropdown.component.scss'],
})
export class CategoryDropdownComponent implements OnInit, OnDestroy {

  @Input()
  creation = false;

  @Input()
  set allCategories(emptyCategory: boolean) {
    this.allCategories_ = emptyCategory;
    this.search();
  }

  @Input()
  set categoryIdActive(categoryId: number | null) {
    this.idCategoryModelActive_ = categoryId;
    this.categoryActive_ = this.categories?.find(cat => cat.id === categoryId);
  }

  @Output()
  changeCategory = new EventEmitter<ICategory | CategoryCompleteModel | null>();

  @Output()
  errorEmitter = new EventEmitter<string>();

  query = '';
  allCategories_ = false;
  categoryActive_: ICategory | undefined;
  idCategoryModelActive_: number | null = null;
  categories: ICategory[] | null = [];
  categorySelectedByLevel: CategoryCompleteModel[] = [];
  subscription: Subscription | null = null;
  categoryUpdatedSubscription: Subscription | null = null;
  subscriptionListen: Subscription | null = null;

  private _transformer = (node: CategoryCompleteModel) => {
    return node;
  };

  // eslint-disable-next-line @typescript-eslint/member-ordering
  treeControl = new FlatTreeControl<CategoryCompleteModel>(
    dataNode => dataNode.level,
    dataNode => dataNode.children && dataNode.children.length > 0
  );

  // eslint-disable-next-line @typescript-eslint/member-ordering
  treeFlattener = new MatTreeFlattener<CategoryCompleteModel, CategoryCompleteModel>(
    this._transformer,
    node => node.level,
    node => node.children && node.children.length > 0,
    node => node.children
  );

  // eslint-disable-next-line @typescript-eslint/member-ordering
  dataSource = new MatTreeFlatDataSource(this.treeControl, this.treeFlattener);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  constructor(private categoryService: CategoryService,
              private confirService: ConfirmationDialogService) {
  }

  hasChild = (_: number, node: CategoryCompleteModel) => node.children && node.children.length > 0;

  isNewNode = (_: number, node: CategoryCompleteModel) => node.id === undefined;

  ngOnInit(): void {

    this.search();

    this.subscriptionListen = this.categoryService.listen().subscribe(values => {
      const type: TypeCategory = values[0];
      if (type === TypeCategory.UPDATED) {
        this.search();
      }
    });

    if (this.categoryUpdatedSubscription) {
      this.categoryUpdatedSubscription.unsubscribe();
    }
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }

    if (this.categoryUpdatedSubscription) {
      this.categoryUpdatedSubscription.unsubscribe();
    }

    if (this.subscriptionListen) {
      this.subscriptionListen.unsubscribe();
    }
  }

  makeActive(node: any): void {
    if (!node || !node.id || node.id === 'undefined') {
      this.idCategoryModelActive_ = null;
      this.categoryActive_ = undefined;
      this.categorySelectedByLevel = [];
      this.changeCategory.emit();
    } else {
      const categoryId = node.id;
      this.idCategoryModelActive_ = categoryId;
      this.categoryActive_ = this.categories?.find(a => a.id === categoryId);
      this.changeCategory.emit(this.categoryActive_);
      this.selectDescendants(categoryId);
    }
  }

  private selectDescendants(id: number): void {
    this.categorySelectedByLevel = [];
    let nodeToFind = this.findInArray(this.dataSource.data, id);
    if (nodeToFind) {
      for (let level = nodeToFind.level - 1; level >= 0; level--) {
        const parentFound = this.findParentInArray(this.dataSource.data, nodeToFind);
        if (parentFound) {
          this.categorySelectedByLevel[level] = parentFound;
        }
        nodeToFind = parentFound;
      }
    }
  }

  private findParentInArray(array: CategoryCompleteModel[], categoryToFind: CategoryCompleteModel | undefined): CategoryCompleteModel | undefined {
    if (!categoryToFind) {
      return undefined;
    }
    for (const element of array) {
      if (element.id === categoryToFind.categoryParentId) {
        return element;
      } else {
        const el = this.findParentInArray(element.children, categoryToFind);
        if (el) {
          return el;
        }
      }
    }
    return undefined;
  }

  private findInArray(array: CategoryCompleteModel[], id: number): CategoryCompleteModel | undefined {
    for (const element of array) {
      if (element.id === id) {
        return element;
      } else {
        const el = this.findInArray(element.children, id);
        if (el) {
          return el;
        }
      }
    }
    return undefined;
  }

  private orderCategoryTree(categories: ICategory[] | null): Observable<CategoryCompleteModel[]> {
    if (!categories) return of([]);

    return from(categories).pipe(
      map(cat => new CategoryCompleteModel(cat, [])),
      toArray(),
      map(allCategories => {
        // Récupération des enfants dans les parents
        const toRemoveFromRoot: CategoryCompleteModel[] = [];
        for (const cat of allCategories) {
          cat.children = [];
          cat.children = allCategories.filter(c => c.categoryParentId === cat.id);
          cat.children.forEach(child => toRemoveFromRoot.push(child));
        }
        toRemoveFromRoot.forEach((child: CategoryCompleteModel) => {
          allCategories.splice(allCategories.indexOf(child), 1);
        });
        return allCategories;
      }),
      map(cats => this.changeLevel(cats, 0))
    );
  }

  private changeLevel(categories: CategoryCompleteModel[], level: number): CategoryCompleteModel[] {
    categories.forEach(cat => {
      cat.level = level;
      if (cat.children && cat.children.length > 0) {
        this.changeLevel(cat.children, level + 1);
      }
    });
    return categories;
  }

  addNewItem(node: CategoryCompleteModel): void {
    node.children.push(new CategoryCompleteModel(new Category(undefined, '', false, node.id, [], []), [], node.level + 1));
    const a = this.dataSource.data;
    this.dataSource.data = a;
    this.treeControl.expand(node);
  }

  expand(): void {
    this.treeControl.expandAll();
  }

  collapse(): void {
    this.treeControl.collapseAll();
  }

  saveItem(node: any, itemValue: HTMLInputElement): void {
    node.name = itemValue.value;
    this.categoryService.create(node)
      .subscribe(
        entityResponse => {
          this.search();
          this.categoryService.emit(TypeCategory.CREATED, entityResponse.body);
        },
        error => this.errorEmitter.emit(error)
      );
  }

  deleteNewItem(node: any): void {
    let index: number | undefined;
    let parentChidrenArray : CategoryCompleteModel[] | undefined;
    index = this.dataSource.data.findIndex(child => child.uid === node.uid);
    parentChidrenArray = this.dataSource.data;
    if (index === undefined || index === -1) {
      parentChidrenArray = this.findParentInArray(this.dataSource.data, node)?.children;
      index = parentChidrenArray?.findIndex(child => child.uid === node.uid);
    }
    if (parentChidrenArray && index !== undefined && index > -1) {
      parentChidrenArray.splice(index, 1);
      const a = this.dataSource.data;
      this.dataSource.data = a;
    }
  }

  deleteItem(node: any): void {
    this.confirService.confirm('Suppression d\'une catégorie', 'Etes vous sûr de vouloir supprimer une catégorie ?')
      .then(isConfirmed => {
        if (isConfirmed) {
          this.categoryService.delete(node.id)
            .subscribe(
              () => this.search(),
              error => this.errorEmitter.emit(error)
            );
        }
      })
      .catch(error => this.errorEmitter.emit(error));
  }

  addNewCategoryToRoot(): void {
    const a = this.dataSource.data;
    a.push(new CategoryCompleteModel(new Category(undefined, '', false, undefined, [], []), [], 0));
    this.dataSource.data = a;
  }

  search(): void {
    const inputValue: string = this.query || '';
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
    this.subscription = of(inputValue)
      .pipe(
        delay(300),
        distinct(),
        concatMap(searchString => this.searchText(searchString))
      ).subscribe(catComplete => {
          this.dataSource.data = catComplete;
          this.treeControl.expandAll();
          if (this.idCategoryModelActive_) {
            this.selectDescendants(this.idCategoryModelActive_);
          }
        },
        error => this.errorEmitter.emit('categoryUid-dropdown.component error : ' + error)
      );
  }

  private searchText(searchString: string): Observable<CategoryCompleteModel[]> {
    return this.categoryService.search({query: searchString})
      .pipe(
        map(a => a.body),
        tap(a => (this.categories = a)),
        concatMap(values => this.orderCategoryTree(values))
      );
  }

  cleanSearch(): void {
    this.query = '';
    this.search();
  }
}
