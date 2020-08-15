import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'category',
        loadChildren: () => import('./category/category.module').then(m => m.EcommerceCategoryModule),
      },
      {
        path: 'attribut',
        loadChildren: () => import('./attribut/attribut.module').then(m => m.EcommerceAttributModule),
      },
      {
        path: 'attribut-value',
        loadChildren: () => import('./attribut-value/attribut-value.module').then(m => m.EcommerceAttributValueModule),
      },
      {
        path: 'attribut-article',
        loadChildren: () => import('./attribut-article/attribut-article.module').then(m => m.EcommerceAttributArticleModule),
      },
      {
        path: 'article',
        loadChildren: () => import('./article/article.module').then(m => m.EcommerceArticleModule),
      },
      {
        path: 'photo',
        loadChildren: () => import('./photo/photo.module').then(m => m.EcommercePhotoModule),
      },
      {
        path: 'model',
        loadChildren: () => import('./model/model.module').then(m => m.EcommerceModelModule),
      },
      {
        path: 'client',
        loadChildren: () => import('./client/client.module').then(m => m.EcommerceClientModule),
      },
      {
        path: 'commande',
        loadChildren: () => import('./commande/commande.module').then(m => m.EcommerceCommandeModule),
      },
      {
        path: 'ligne-commande',
        loadChildren: () => import('./ligne-commande/ligne-commande.module').then(m => m.EcommerceLigneCommandeModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EcommerceEntityModule {}
