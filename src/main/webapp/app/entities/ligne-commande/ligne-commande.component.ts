import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILigneCommande } from 'app/shared/model/ligne-commande.model';
import { LigneCommandeService } from './ligne-commande.service';
import { LigneCommandeDeleteDialogComponent } from './ligne-commande-delete-dialog.component';

@Component({
  selector: 'jhi-ligne-commande',
  templateUrl: './ligne-commande.component.html',
})
export class LigneCommandeComponent implements OnInit, OnDestroy {
  ligneCommandes?: ILigneCommande[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected ligneCommandeService: LigneCommandeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected activatedRoute: ActivatedRoute
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll(): void {
    if (this.currentSearch) {
      this.ligneCommandeService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<ILigneCommande[]>) => (this.ligneCommandes = res.body || []));
      return;
    }

    this.ligneCommandeService.query().subscribe((res: HttpResponse<ILigneCommande[]>) => (this.ligneCommandes = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInLigneCommandes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ILigneCommande): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInLigneCommandes(): void {
    this.eventSubscriber = this.eventManager.subscribe('ligneCommandeListModification', () => this.loadAll());
  }

  delete(ligneCommande: ILigneCommande): void {
    const modalRef = this.modalService.open(LigneCommandeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.ligneCommande = ligneCommande;
  }
}
