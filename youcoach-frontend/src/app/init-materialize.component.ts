import {AfterViewInit} from '@angular/core';
import * as M from 'materialize-css';

export abstract class InitMaterializeComponent implements AfterViewInit {

  ngAfterViewInit(): void {
    M.AutoInit();
  }
}
