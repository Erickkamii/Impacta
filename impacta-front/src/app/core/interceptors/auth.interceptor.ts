import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    console.log('🔄 Interceptor executando para:', req.url);

    const token = this.getAuthToken();

    if (token) {
      console.log('✅ Token encontrado:', token);
      const authReq = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
      console.log('📤 Headers enviados:', authReq.headers.get('Authorization'));
      return next.handle(authReq);
    } else {
      console.log('❌ Nenhum token encontrado');
    }

    return next.handle(req);
  }

  private getAuthToken(): string | null {
    try {
      const auth = localStorage.getItem('authToken');
      console.log('🔍 localStorage authToken:', auth);

      if (auth) {
        // Tenta primeiro como JSON
        try {
          const parsedAuth = JSON.parse(auth);
          console.log('📋 Dados parseados como JSON:', parsedAuth);
          return parsedAuth?.token || null;
        } catch {
          // Se falhar, assume que é o token direto
          console.log('📋 Usando token direto (não é JSON):', auth);
          return auth;
        }
      }
    } catch (error) {
      console.error('❌ Erro ao recuperar token:', error);
    }
    return null;
  }
}
