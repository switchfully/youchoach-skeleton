import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppComponent} from './app.component';
import {FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from '@angular/common/http';
import {AppRoutingModule} from './app-routing.module';
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatButtonModule} from '@angular/material/button';
import {_MatMenuDirectivesModule, MatMenuModule} from '@angular/material/menu';
import {MatIconModule} from '@angular/material/icon';
import {RedirectOnErrorInterceptor} from './redirect-on-error.interceptor';
import {CustomFormsModule} from 'ngx-custom-validators';
import {SessionModule} from "./session/session.module";
import {SecurityModule} from "./security/security.module";
import {FooterComponent} from "./template/footer/footer.component";
import {HeaderComponent} from "./template/header/header.component";
import {HomeComponent} from "./template/home/home.component";
import {NotFoundComponent} from "./template/not-found/not-found.component";
import {ProfileModule} from "./profile/profile.module";


export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

@NgModule({
  declarations: [
    AppComponent,
    FooterComponent,
    HeaderComponent,
    HomeComponent,
    NotFoundComponent
  ],

  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    AppRoutingModule,
    TranslateModule.forRoot({
      defaultLanguage: 'en',
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
    BrowserAnimationsModule,
    MatButtonModule,
    _MatMenuDirectivesModule,
    MatMenuModule,
    MatIconModule,
    CustomFormsModule,
    SessionModule,
    SecurityModule,
    ProfileModule
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: RedirectOnErrorInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
