import {EventEmitter, Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ProfileViewService {
  change: EventEmitter<any> = new EventEmitter<any>();

  constructor() { }

  disableCoachView() {
    this.change.emit(false);
  }

  enableCoachView() {
    this.change.emit(true);
  }
}
