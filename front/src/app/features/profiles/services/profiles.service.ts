import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Profile } from 'src/app/features/profiles/interfaces/profile.interface';
import { ProfileResponse } from '../interfaces/api/profileResponse.interface';
import { ProfilesResponse } from '../interfaces/api/profilesResponse.interface';
import { environment as prodenvir} from '../../../../environments/environment.prod'; 
import { environment as envir} from '../../../../environments/environment'; 


@Injectable({
  providedIn: 'root'
})
export class ProfilesService {
  private pathService = prodenvir.production ? `${prodenvir.baseUrl}profiles`: `${envir.baseUrl}profiles`;

  constructor(private httpClient: HttpClient) { }

  public all(): Observable<ProfilesResponse> {
    return this.httpClient.get<ProfilesResponse>(this.pathService);
  }

  public detail(id: string): Observable<Profile> {
    return this.httpClient.get<Profile>(`${this.pathService}/${id}`);
  }

  public create(form: FormData): Observable<ProfileResponse> {
    return this.httpClient.post<ProfileResponse>(this.pathService, form);
  }

  public update(id: string, form: FormData): Observable<ProfileResponse> {
    return this.httpClient.put<ProfileResponse>(`${this.pathService}/${id}`, form);
  }
}
