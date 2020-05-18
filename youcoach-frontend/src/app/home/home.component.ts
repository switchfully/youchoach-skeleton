import {Component, OnInit} from '@angular/core';
import {InitMaterializeComponent} from '../init-materialize.component';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent extends InitMaterializeComponent {

  constructor() {
    super();
  }

}
