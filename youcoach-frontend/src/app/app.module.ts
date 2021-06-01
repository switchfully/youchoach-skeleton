import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppComponent} from './app.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {AppRoutingModule} from './app-routing.module';
import {RedirectOnErrorInterceptor} from './redirect-on-error.interceptor';
import {SessionModule} from "./session/session.module";
import {SecurityModule} from "./security/security.module";
import {FooterComponent} from "./template/footer/footer.component";
import {HeaderComponent} from "./template/header/header.component";
import {HomeComponent} from "./template/home/home.component";
import {NotFoundComponent} from "./template/not-found/not-found.component";
import {ProfileModule} from "./profile/profile.module";
import {CoacheeComponent} from './template/coachee/coachee.component';
import {CoachComponent} from './template/coach/coach.component';
import {EnHomeComponent} from './template/home/en-home/en-home.component';
import {FrHomeComponent} from './template/home/fr-home/fr-home.component';
import {AdminModule} from "./admin/admin.module";
import {AdminComponent} from './template/admin/admin.component';
import {FaqComponent} from './template/faq/faq.component';
import {EnFaqComponent} from './template/faq/en-faq/en-faq.component';
import {FrFaqComponent} from './template/faq/fr-faq/fr-faq.component';

@NgModule({
  declarations: [
    AppComponent,
    FooterComponent,
    HeaderComponent,
    HomeComponent,
    NotFoundComponent,
    CoacheeComponent,
    CoachComponent,
    EnHomeComponent,
    FrHomeComponent,
    AdminComponent,
    FaqComponent,
    EnFaqComponent,
    FrFaqComponent
  ],

  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    AppRoutingModule,
    SessionModule,
    SecurityModule,
    ProfileModule,
    AdminModule
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: RedirectOnErrorInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
