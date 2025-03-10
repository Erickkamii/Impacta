import { Component } from '@angular/core';
import { DefaultLoginLayoutComponent } from '../../components/default-login-layout/default-login-layout.component';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { PrimaryInputComponent } from '../../components/primary-input/primary-input.component';
import { Router } from '@angular/router';
import { LoginService } from '../../services/login.service';
import { ToastrService } from 'ngx-toastr';
import { DefaultSignupLayoutComponent } from '../../components/default-signup-layout/default-signup-layout.component';

interface SignupForm {
  name: FormControl,
  email: FormControl,
  password: FormControl,
  passwordConfirmation: FormControl,
  document: FormControl
}
@Component({
  selector: 'app-login',
  imports: [
    DefaultSignupLayoutComponent,
    ReactiveFormsModule,
    PrimaryInputComponent
  ],
  providers:[
    LoginService
  ],
  templateUrl: './signup-ong.component.html',
  styleUrl: './signup-ong.component.scss'
})
export class SignupOngComponent {
  signupForm!: FormGroup<SignupForm>;
  constructor(
    private router: Router,
    private loginService: LoginService,
    private toastService: ToastrService
  ){
    this.signupForm = new FormGroup({
      name: new FormControl('',[Validators.required, Validators.minLength(3)]),
      email: new FormControl('',[Validators.required, Validators.email]),
      password: new FormControl('',[Validators.required, Validators.minLength(8)]),
      passwordConfirmation: new FormControl('',[Validators.required, Validators.minLength(8)]),
      document: new FormControl('',[Validators.required, Validators.minLength(8)])
    })
  }

  submit(){
    this.loginService.login(this.signupForm.value.email,this.signupForm.value.password).subscribe({
      next: () => this.toastService.success("Cadastro realizado com sucesso"),
      error: () => this.toastService.error("Falha no Cadastro, tente novamente mais tarde!")
    })
  }

  navigate(){
    this.router.navigate(['login'])
  }

  navigateSign(){
    console.log("hey")
    this.router.navigate(['sign-up'])
  }
}
