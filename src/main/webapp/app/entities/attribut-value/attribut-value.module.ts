import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EcommerceSharedModule } from 'app/shared/shared.module';
import { AttributValueComponent } from './attribut-value.component';
import { AttributValueDetailComponent } from './attribut-value-detail.component';
import { AttributValueUpdateComponent } from './attribut-value-update.component';
import { AttributValueDeleteDialogComponent } from './attribut-value-delete-dialog.component';
import { attributValueRoute } from './attribut-value.route';

@NgModule({
  imports: [EcommerceSharedModule, RouterModule.forChild(attributValueRoute)],
  declarations: [AttributValueComponent, AttributValueDetailComponent, AttributValueUpdateComponent, AttributValueDeleteDialogComponent],
  entryComponents: [AttributValueDeleteDialogComponent],
})
export class EcommerceAttributValueModule {}
