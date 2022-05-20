import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Patient } from './patient';
import { PatientService } from './patient.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  public patients!: Patient[];
  public editPatient!: Patient;
  public deletePatient!: Patient;

  constructor(private patientService: PatientService) {}

  ngOnInit(): void {
    this.getPatients();
  }

  public getPatients(): void {
    this.patientService.getPatients().subscribe(
      (response: Patient[]) => {
        this.patients = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }


  public searchPatients(searchWord: string): void {
    const results: Patient[] = [];
    this.patients.forEach(patient => {
      if(patient.name.toLowerCase().indexOf(searchWord.toLowerCase()) !== -1
      || patient.vorname.toLowerCase().indexOf(searchWord.toLocaleLowerCase()) !== -1
      || patient.versichertennummer.toLowerCase().indexOf(searchWord.toLowerCase()) !== -1
      || patient.kassenname.toLowerCase().indexOf(searchWord.toLowerCase()) !== -1
      || patient.ik === parseInt(searchWord) ){
        results.push(patient);
      }
    });
    this.patients = results;
    if(results.length === 0 || !searchWord) {
      this.getPatients();
    }
  }

  public onAddPatient(addForm: NgForm): void {
    this.patientService.addPatient(addForm.value).subscribe(
      (response: Patient) => {
        console.log(response);
        this.getPatients();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
    document.getElementById('addFormDismissBtn')?.click();
  }

  public onUpdatePatient(updateForm: NgForm): void {
    this.patientService.updatePatient(updateForm.value).subscribe(
      (response: Patient) => {
        console.log(response);
        this.getPatients();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
    document.getElementById('updateFormDismissBtn')?.click();
  }

  public onDeletePatient(patientId: number):void {
    this.patientService.deletePatient(patientId).subscribe(
      (response: void) => {
        console.log(response);
        this.getPatients();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
    document.getElementById('deleteFormDismissBtn')?.click();
  }

  public onOpenUpdateModal(patient: Patient): void {
    this.editPatient = patient;
    document.getElementById('updateModalBtn')?.click();
  }

  public onOpenDeleteModal(patient: Patient): void {
    this.deletePatient = patient;
    document.getElementById('deleteModalBtn')?.click();
  }
}
