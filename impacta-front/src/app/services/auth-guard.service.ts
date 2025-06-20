import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(
    private router: Router,
    private toastService: ToastrService
  ) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const token = localStorage.getItem('authToken');
    const userRole = localStorage.getItem('userRole');

    // Verificar se o usuário está autenticado
    if (!token) {
      this.toastService.error('Você precisa fazer login para acessar esta página');
      this.router.navigate(['/login']);
      return false;
    }

    // Verificar se a rota tem restrição de role
    const requiredRole = route.data['role'];
    if (requiredRole && userRole !== requiredRole) {
      this.toastService.error('Você não tem permissão para acessar esta página');

      // Redirecionar para o dashboard correto baseado no role do usuário
      if (userRole === 'ONG') {
        this.router.navigate(['/dashboard-ong']);
      } else if (userRole === 'VOLUNTEER') {
        this.router.navigate(['/dashboard-volunteer']);
      } else {
        this.router.navigate(['/login']);
      }
      return false;
    }

    return true;
  }
}

// Service para gerenciar autenticação
@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private router: Router) {}

  isAuthenticated(): boolean {
    return !!localStorage.getItem('authToken');
  }

  getUserRole(): 'ONG' | 'VOLUNTEER' | null {
    return localStorage.getItem('userRole') as 'ONG' | 'VOLUNTEER' | null;
  }

  getUserName(): string | null {
    return localStorage.getItem('userName');
  }

  logout(): void {
    localStorage.removeItem('authToken');
    localStorage.removeItem('userRole');
    localStorage.removeItem('userName');
    this.router.navigate(['/login']);
  }

  // Método para verificar se o token ainda é válido (você pode implementar verificação de expiração)
  isTokenValid(): boolean {
    const token = localStorage.getItem('authToken');
    if (!token) return false;

    try {
      // Decodificar o JWT para verificar expiração (implementação básica)
      const payload = JSON.parse(atob(token.split('.')[1]));
      const currentTime = Math.floor(Date.now() / 1000);

      return payload.exp > currentTime;
    } catch (error) {
      return false;
    }
  }
}
