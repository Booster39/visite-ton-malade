import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { SessionService } from 'src/app/services/session.service';
import { ProfileResponse } from '../../interfaces/api/profileResponse.interface';
import { Profile } from '../../interfaces/profile.interface';
import { ProfilesService } from '../../services/profiles.service';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.scss']
})
export class FormComponent implements OnInit {

  public onUpdate: boolean = false;
  public profileForm: FormGroup | undefined;

  private id: string | undefined;

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private matSnackBar: MatSnackBar,
    private profilesService: ProfilesService,
    private sessionService: SessionService,
    private router: Router
  ) {
  }

  public ngOnInit(): void {
    const url = this.router.url;
    if (url.includes('update')) {
      this.onUpdate = true;
      this.id = this.route.snapshot.paramMap.get('id')!;
      this.profilesService
        .detail(this.id)
        .subscribe((profile: Profile) => this.initForm(profile));
    } else {
      this.initForm();
    }
  }

  public submit(): void {
    const formData = new FormData();
    formData.append('name', this.profileForm!.get('name')?.value);
    formData.append('age', this.profileForm!.get('age')?.value);
    formData.append('city', this.profileForm!.get('city')?.value);
    if (!this.onUpdate) {
      formData.append('picture', this.profileForm!.get('picture')?.value._files[0]);
    }
    formData.append('description', this.profileForm!.get('description')?.value);

    if (!this.onUpdate) {
      this.profilesService
        .create(formData)
        .subscribe((profileResponse: ProfileResponse) => this.exitPage(profileResponse));
    } else {
      this.profilesService
        .update(this.id!, formData)
        .subscribe((profileResponse: ProfileResponse) => this.exitPage(profileResponse));
    }
  }

  private initForm(profile?: Profile): void {
    console.log(profile);
    console.log(this.sessionService.user!.id);
    if( (profile !== undefined) && (profile?.owner_id !== this.sessionService.user!.id)) {
      this.router.navigate(['/profiles']);
    }
    this.profileForm = this.fb.group({
      name: [profile ? profile.name : '', [Validators.required]],
      age: [profile ? profile.age : '', [Validators.required]],
      city: [profile ? profile.city : '', [Validators.required]],
      description: [profile ? profile.description : '', [Validators.required]],
    });
    if (!this.onUpdate) {
      this.profileForm.addControl('picture', this.fb.control('', [Validators.required]));
    }
  }

  private exitPage(profileResponse: ProfileResponse): void {
    this.matSnackBar.open(profileResponse.message, "Close", { duration: 3000 });
    this.router.navigate(['profiles']);
  }
}