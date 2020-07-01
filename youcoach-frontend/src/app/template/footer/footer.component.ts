import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ThemeService} from "../theme.service";


@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: []
})
export class FooterComponent implements OnInit {

  constructor(public themeService: ThemeService) { }


  ngOnInit(): void {
  }
}
