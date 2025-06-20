import { Component } from '@angular/core';
import { DefaultSignupLayoutComponent } from '../../components/default-signup-layout/default-signup-layout.component';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { PrimaryInputComponent } from '../../components/primary-input/primary-input.component';
import { Router } from '@angular/router';
import { LoginService } from '../../services/login.service';
import { ToastrService } from 'ngx-toastr';

interface SignupForm {
  name: FormControl,
  email: FormControl,
  password: FormControl,
  passwordConfirmation: FormControl,
  document: FormControl
}

interface LoginResponse {
  name: string;
  token: string;
  role: string;
}

@Component({
  selector: 'app-signup-volunteer',
  imports: [
    DefaultSignupLayoutComponent,
    ReactiveFormsModule,
    PrimaryInputComponent
  ],
  providers:[
    LoginService
  ],
  templateUrl: './signup-volunteer.component.html',
  styleUrl: './signup-volunteer.component.scss'
})
export class SignupVolunteerComponent {
  signupForm!: FormGroup<SignupForm>;

  constructor(
    private router: Router,
    private loginService: LoginService,
    private toastService: ToastrService
  ){
    this.signupForm = new FormGroup({
      name: new FormControl('', [Validators.required, Validators.minLength(3)]),
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required, Validators.minLength(8)]),
      passwordConfirmation: new FormControl('', [Validators.required, Validators.minLength(8)]),
      document: new FormControl('', [Validators.required, Validators.minLength(8)])
    }, { validators: this.passwordMatchValidator as any });
  }

  // Validador customizado para verificar se as senhas coincidem
  private passwordMatchValidator(control: import('@angular/forms').AbstractControl): {[key: string]: any} | null {
    const password = control.get('password');
    const passwordConfirmation = control.get('passwordConfirmation');

    if (password && passwordConfirmation && password.value !== passwordConfirmation.value) {
      return { 'passwordMismatch': true };
    }
    return null;
  }

  submit(){
    if (this.signupForm.valid) {
      const { name, email, password, document } = this.signupForm.value;

      this.loginService.register(name, email, password, document, "VOLUNTEER").subscribe({
        next: (response: LoginResponse) => {
          // Salvar token no localStorage ou sessionStorage se necessário
          localStorage.setItem('authToken', response.token);
          localStorage.setItem('userRole', response.role);
          localStorage.setItem('userName', response.name);

          this.toastService.success("Cadastro realizado com sucesso!");

          // Redirecionamento baseado no role
          this.redirectUserBasedOnRole(response.role as 'ONG' | 'VOLUNTEER');
        },
        error: (error) => {
          console.error('Erro no cadastro:', error);
          this.toastService.error("Falha no cadastro, tente novamente mais tarde!");
        }
      });
    } else {
      // Mostrar erros de validação
      if (this.signupForm.hasError('passwordMismatch')) {
        this.toastService.error("As senhas não coincidem!");
      } else {
        this.toastService.error("Por favor, preencha todos os campos corretamente!");
      }
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
    this.router.navigate(['/login']);
  }

  navigateSign(){
    this.router.navigate(['/signup-ong']);
  }
}
