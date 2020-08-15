import {NgModule} from '@angular/core';
import {EcommerceSharedLibsModule} from './shared-libs.module';
import {FindLanguageFromKeyPipe} from './language/find-language-from-key.pipe';
import {AlertComponent} from './alert/alert.component';
import {AlertErrorComponent} from './alert/alert-error.component';
import {LoginModalComponent} from './login/login.component';
import {HasAnyAuthorityDirective} from './auth/has-any-authority.directive';
import {ConfirmationDialogComponent} from "app/shared/confirm/confirmation-dialog.component";
import {ConfirmationDialogService} from "app/shared/confirm/ConfirmationDialogService";

@NgModule({
  imports: [EcommerceSharedLibsModule],
  declarations: [FindLanguageFromKeyPipe, AlertComponent, AlertErrorComponent, LoginModalComponent, HasAnyAuthorityDirective, ConfirmationDialogComponent,],
  entryComponents: [LoginModalComponent],
  exports: [
    EcommerceSharedLibsModule,
    FindLanguageFromKeyPipe,
    AlertComponent,
    AlertErrorComponent,
    LoginModalComponent,
    HasAnyAuthorityDirective,
    ConfirmationDialogComponent,
  ],
  providers: [ConfirmationDialogService]
})
export class EcommerceSharedModule {
}
