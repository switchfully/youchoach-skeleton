import { Component, OnInit } from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['../../css/materialize.css', '../../css/style.css']
})
export class RegisterComponent implements OnInit {
  // registerForm = this.fb.group({
  //   firstname: ['', [Validators.required]],
  //   lastname: ['', [Validators.required]],
  //   email: ['', [Validators.required]],
  //   password: ['', [Validators.required]]
  // });
  // addItemForm = this.fb.group({
  //   name: ['', [required]],
  //   description: ['', [required, Validators.maxLength(this.maxLength)]],
  //   price: ['', [required, Validators.min(0)]],
  //   amountOfStock: ['', [required, Validators.min(0)]]
  // });


  constructor(private fb: FormBuilder) { }

  ngOnInit(): void {
  }

}
