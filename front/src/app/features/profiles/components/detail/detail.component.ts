import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { Profile } from 'src/app/features/profiles/interfaces/profile.interface';
import { SessionService } from 'src/app/services/session.service';
import { MessageRequest } from '../../interfaces/api/messageRequest.interface';
import { MessageResponse } from '../../interfaces/api/messageResponse.interface';
import { MessagesService } from '../../services/messages.service';
import { ProfilesService } from '../../services/profiles.service';

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss']
})
export class DetailComponent implements OnInit {

  public messageForm!: FormGroup;
  public profile: Profile | undefined;

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private messagesService: MessagesService,
    private profilesService: ProfilesService,
    private sessionService: SessionService,
    private matSnackBar: MatSnackBar) {
    this.initMessageForm();
  }

  public ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id')!

    this.profilesService
      .detail(id)
      .subscribe((profile: Profile) => this.profile = profile);
  }

  public back() {
    window.history.back();
  }

  public sendMessage(): void {
    const message = {
      profile_id: this.profile!.id,
      user_id: this.sessionService.user?.id,
      message: this.messageForm.value.message
    } as MessageRequest;

    this.messagesService.send(message).subscribe(
      (messageResponse: MessageResponse) => {
        this.initMessageForm();
        this.matSnackBar.open(messageResponse.message, "Close", { duration: 3000 });
      });
  }

  private initMessageForm() {
    this.messageForm = this.fb.group({
      message: ['', [Validators.required, Validators.min(10)]],
    });
  }

}
