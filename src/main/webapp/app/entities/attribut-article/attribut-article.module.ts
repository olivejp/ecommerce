import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EcommerceSharedModule } from 'app/shared/shared.module';
import { AttributArticleComponent } from './attribut-article.component';
import { AttributArticleDetailComponent } from './attribut-article-detail.component';
import { AttributArticleUpdateComponent } from './attribut-article-update.component';
import { AttributArticleDeleteDialogComponent } from './attribut-article-delete-dialog.component';
import { attributArticleRoute } from './attribut-article.route';

@NgModule({
  imports: [EcommerceSharedModule, RouterModule.forChild(attributArticleRoute)],
  declarations: [
    AttributArticleComponent,
    AttributArticleDetailComponent,
    AttributArticleUpdateComponent,
    AttributArticleDeleteDialogComponent,
  ],
  entryComponents: [AttributArticleDeleteDialogComponent],
})
export class EcommerceAttributArticleModule {}
