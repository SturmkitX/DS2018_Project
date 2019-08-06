import { UserChannelComponent } from './components/user-channel/user-channel.component';
import { ChannelsComponent } from './components/channels/channels.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { SettingsComponent } from './components/settings/settings.component';
import { InboxComponent } from './components/inbox/inbox.component';
import { ViewChannelComponent } from './components/view-channel/view-channel.component';

const routes: Routes = [
    // { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
    { path: 'home', component: DashboardComponent },
    { path: 'settings', component: SettingsComponent },
    { path: 'channels', component: ChannelsComponent },
    { path: 'my-channel', component: UserChannelComponent },
    { path: 'inbox', component: InboxComponent },
    { path: 'channel/:id', component: ViewChannelComponent }
];

@NgModule({
    exports: [RouterModule],
    imports: [RouterModule.forRoot(routes)]
})
export class AppRoutingModule {

}
