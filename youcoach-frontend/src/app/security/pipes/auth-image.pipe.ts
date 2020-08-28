import {Pipe, PipeTransform} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {DomSanitizer} from "@angular/platform-browser";
import {Observable, of} from "rxjs";
import {catchError, map} from "rxjs/operators";
import {ImageService} from "../services/image.service";

@Pipe({
  name: 'authImage'
})
export class AuthImagePipe implements PipeTransform {

  constructor(
    private imageService: ImageService
  ) {
  }

  transform(profileId: string): Observable<any> {
    return this.imageService.downloadImageAsUrl(profileId);
  }
}
