import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ForbiddenService {

  private _url = '';

  constructor() { }

  get url(): string {
    return this._url;
  }

  set url(url: string) {
    this._url = url;
  }
}
