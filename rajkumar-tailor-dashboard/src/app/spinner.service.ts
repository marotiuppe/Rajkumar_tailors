import { Subject } from 'rxjs';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root',
  })
  export class SpinnerService {
    
    spinnerSubject = new Subject<boolean>();
    constructor() {}

    showSpinner(){
      this.spinnerSubject.next(true);
    }
    hideSpinner(){
      this.spinnerSubject.next(false);
    }

    readSpinner(){
      return this.spinnerSubject.asObservable();
    }
}
