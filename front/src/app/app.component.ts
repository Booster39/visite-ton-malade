import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from './features/auth/services/auth.service';
import { User } from './interfaces/user.interface';
import { SessionService } from './services/session.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  constructor(
    private authService: AuthService,
    private router: Router,
    private sessionService: SessionService) {
  }

  public isLoading = true;

  public ngOnInit(): void {
    this.autoLog();
  }

  public logout(): void {
    this.sessionService.logOut();
    this.router.navigate([''])
  }
  public showConnexionButtons(): boolean {
    return this.isLoading || (this.sessionService.isLogged && !this.router.url.includes('profiles'));
  }

  public autoLog(): void {
    this.isLoading = true;
    this.authService.me().subscribe(
      (user: User) => {
        this.sessionService.logIn(user);
        this.isLoading = false;
      },
      (_) => {
        this.sessionService.logOut();
        this.isLoading = false;
      }
    )
  }
}
