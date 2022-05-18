import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Patient } from './patient';

@Injectable({
  providedIn: 'root'
})
export class PatientService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public getPatients(): Observable<Patient[]> {
      return this.http.get<Patient[]>(`${this.apiServerUrl}/patient`);
  }

  public getPatient(id: number): Observable<Patient> {
      return this.http.get<Patient>(`${this.apiServerUrl}/patient/${id}`);
  }

  public addPatient(patient: Patient): Observable<Patient> {
      return this.http.post<Patient>(`${this.apiServerUrl}/patient/add`, patient);
  }

  public updatePatient(patient: Patient): Observable<Patient> {
      return this.http.put<Patient>(`${this.apiServerUrl}/patient/update`, patient);
  }

  public deletePatient(id: number): Observable<void> {
      return this.http.delete<void>(`${this.apiServerUrl}/patient/delete/${id}`);
  }

  
}
