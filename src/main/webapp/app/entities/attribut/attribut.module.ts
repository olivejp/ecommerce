import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EcommerceSharedModule } from 'app/shared/shared.module';
import { AttributComponent } from './attribut.component';
import { AttributDetailComponent } from './attribut-detail.component';
import { AttributUpdateComponent } from './attribut-update.component';
import { AttributDeleteDialogComponent } from './attribut-delete-dialog.component';
import { attributRoute } from './attribut.route';

@NgModule({
  imports: [EcommerceSharedModule, RouterModule.forChild(attributRoute)],
  declarations: [AttributComponent, AttributDetailComponent, AttributUpdateComponent, AttributDeleteDialogComponent],
  entryComponents: [AttributDeleteDialogComponent],
})
export class EcommerceAttributModule {}
