import {AfterViewInit, Component, OnInit} from '@angular/core';
import {InitService} from "../../materialize/init.service";

@Component({
  selector: 'app-fr-home',
  templateUrl: './fr-home.component.html',
  styleUrls: ['./fr-home.component.css']
})
export class FrHomeComponent implements OnInit, AfterViewInit {

  constructor(private initService: InitService) { }

  ngOnInit(): void {
  }

  ngAfterViewInit(): void {
    this.initService.initParalax();
  }
}
