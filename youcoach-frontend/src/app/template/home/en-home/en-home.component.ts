import {AfterViewInit, Component, OnInit} from '@angular/core';
import {InitService} from "../../materialize/init.service";

@Component({
  selector: 'app-en-home',
  templateUrl: './en-home.component.html',
  styleUrls: ['./en-home.component.css']
})
export class EnHomeComponent implements OnInit, AfterViewInit {

  constructor(private initService: InitService) { }

  ngOnInit(): void {
  }

  ngAfterViewInit(): void {
    this.initService.initParalax()
  }
}
