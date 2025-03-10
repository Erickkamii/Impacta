import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { SignupComponent } from './pages/signup-volunteer/signup-volunteer.component';
import { SignupOngComponent } from './pages/signup-ong/signup-ong.component';

export const routes: Routes = [
  {
    path: "login",
    component: LoginComponent
  },
  {
    path: "sign-up",
    component: SignupComponent
  },
  {
    path: "sign-up-ong",
    component: SignupOngComponent
  }
];
