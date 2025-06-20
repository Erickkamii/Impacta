import { Routes } from '@angular/router';
// Update the path below to the correct location of auth.guard.ts, for example:
import { AuthGuard } from './services/auth-guard.service';

// Importar seus componentes
import { LoginComponent } from './pages/login/login.component';
import { SignupVolunteerComponent } from './pages/signup-volunteer/signup-volunteer.component';
import { SignupOngComponent } from './pages/signup-ong/signup-ong.component';
import { DashboardVolunteerComponent } from './pages/dashboard-volunteer/dashboard-volunteer.component';
import { DashboardOngComponent } from './pages/dashboard-ong/dashboard-ong.component';

export const routes: Routes = [
  // Rotas públicas (não precisam de autenticação)
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'signup-volunteer',
    component: SignupVolunteerComponent
  },
  {
    path: 'signup-ong',
    component: SignupOngComponent
  },

  // Rotas protegidas (precisam de autenticação)
  {
    path: 'dashboard-volunteer',
    component: DashboardVolunteerComponent,
    canActivate: [AuthGuard],
    data: { role: 'VOLUNTEER' } // Somente voluntários podem acessar
  },
  {
    path: 'dashboard-ong',
    component: DashboardOngComponent,
    canActivate: [AuthGuard],
    data: { role: 'ONG' } // Somente ONGs podem acessar
  },

  // Rota padrão
  {
    path: '',
    redirectTo: '/login',
    pathMatch: 'full'
  },

  // Rota para páginas não encontradas
  {
    path: '**',
    redirectTo: '/login'
  }
];

