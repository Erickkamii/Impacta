import { Component } from '@angular/core';
import { DefaultLoginLayoutComponent } from '../../components/default-login-layout/default-login-layout.component';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { PrimaryInputComponent } from '../../components/primary-input/primary-input.component';
import { Router } from '@angular/router';
import { LoginService } from '../../services/login.service';
import { ToastrService } from 'ngx-toastr';

interface LoginForm {
  email: FormControl,
  password: FormControl
}

// import the correct LoginResponse type from your types file
import { LoginResponse } from '../../types/login-response.type';

@Component({
  selector: 'app-login',
  imports: [
    DefaultLoginLayoutComponent,
    ReactiveFormsModule,
    PrimaryInputComponent
  ],
  providers:[
    LoginService
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  loginForm!: FormGroup<LoginForm>;

  constructor(
    private router: Router,
    private loginService: LoginService,
    private toastService: ToastrService
  ){
    this.loginForm = new FormGroup({
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required, Validators.minLength(6)])
    })
  }

  submit(){
    if (this.loginForm.valid) {
      const { email, password } = this.loginForm.value;

      this.loginService.login(email, password).subscribe({
        next: (response: LoginResponse) => {
          // Salvar token no localStorage ou sessionStorage se necessário
          localStorage.setItem('authToken', response.token);
          localStorage.setItem('userRole', response.role);
          localStorage.setItem('userName', response.name);

          this.toastService.success(`Bem-vindo, ${response.name}!`);

          // Redirecionamento baseado no role
          this.redirectUserBasedOnRole(response.role as 'ONG' | 'VOLUNTEER');
        },
        error: (error) => {
          console.error('Erro no login:', error);
          this.toastService.error("Falha no login, verifique suas credenciais!");
        }
      });
    }
  }

  private redirectUserBasedOnRole(role: 'ONG' | 'VOLUNTEER'): void {
    switch (role) {
      case 'ONG':
        this.router.navigate(['/dashboard-ong']);
        break;
      case 'VOLUNTEER':
        this.router.navigate(['/dashboard-volunteer']);
        break;
      default:
        this.toastService.error('Tipo de usuário não reconhecido');
        break;
    }
  }

  navigate(){
    this.router.navigate(['/signup-volunteer']); // ou a rota de signup que você preferir
  }
}
