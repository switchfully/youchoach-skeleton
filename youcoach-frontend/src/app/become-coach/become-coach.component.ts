import { Component, OnInit } from '@angular/core';
import {InitMaterializeComponent} from '../init-materialize.component';

@Component({
  selector: 'app-become-coach',
  templateUrl: './become-coach.component.html',
  styleUrls: ['./become-coach.component.css']
})
export class BecomeCoachComponent extends InitMaterializeComponent implements OnInit {

  constructor() {
    super();
  }


  ngOnInit(){
  }
}
