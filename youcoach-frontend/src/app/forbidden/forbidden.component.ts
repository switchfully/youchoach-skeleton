import {Component, Input, OnInit} from '@angular/core';
import {ForbiddenService} from './forbidden.service';

@Component({
  selector: 'app-forbidden',
  templateUrl: './forbidden.component.html',
  styleUrls: ['./forbidden.component.css']
})
export class ForbiddenComponent implements OnInit {
  @Input() previous: string;

  constructor(private forbiddenService: ForbiddenService) {
    this.previous = forbiddenService.url;
  }

  ngOnInit(): void {
  }

}
