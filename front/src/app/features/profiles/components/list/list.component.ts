import { Component } from '@angular/core';
import { User } from 'src/app/interfaces/user.interface';
import { SessionService } from 'src/app/services/session.service';
import { ProfilesService } from '../../services/profiles.service';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent {

  public profiles$ = this.profilesService.all();

  constructor(
    private sessionService: SessionService,
    private profilesService: ProfilesService
  ) { }

  get user(): User | undefined {
    return this.sessionService.user;
  }
}
