import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LoginComponent } from './components/login/login.component';

import { BasicAuthInterceptor } from './services/auth.service';
import { ErrorInterceptor } from './services/error.service';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { ChannelsComponent } from './components/channels/channels.component';
import { SettingsComponent } from './components/settings/settings.component';
import { UserChannelComponent } from './components/user-channel/user-channel.component';
import { ViewChannelComponent } from './components/view-channel/view-channel.component';
import { InboxComponent } from './components/inbox/inbox.component';
import { ChannelEditorComponent } from './components/channel-editor/channel-editor.component';

@NgModule({
    declarations: [
        AppComponent,
        LoginComponent,
        DashboardComponent,
        ChannelsComponent,
        SettingsComponent,
        UserChannelComponent,
        ViewChannelComponent,
        InboxComponent,
        ChannelEditorComponent,
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        FormsModule,
        ReactiveFormsModule,
        HttpClientModule,
    ],
    providers: [
        { provide: HTTP_INTERCEPTORS, useClass: BasicAuthInterceptor, multi: true },
        { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
    ],
    bootstrap: [AppComponent]
})
export class AppModule { }
