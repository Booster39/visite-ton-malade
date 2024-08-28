import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../interfaces/user.interface';
import { environment as prodenvir} from '../../environments/environment.prod'; 
import { environment as envir} from '../../environments/environment'; 

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private pathService = prodenvir.production ? `${prodenvir.baseUrl}user`: `${envir.baseUrl}user`;

  constructor(private httpClient: HttpClient) { }

  public getUserById(id: number): Observable<User> {
    return this.httpClient.get<User>(`${this.pathService}/${id}`);
  }
}
