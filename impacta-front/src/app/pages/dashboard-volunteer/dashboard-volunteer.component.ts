import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DashboardLayoutComponent } from "../../components/dashboard-layout/dashboard-layout.component";

interface Event {
  id: number;
  description: string;
  city: string;
  state: string;
  period?: string;
  status?: string;
}

interface Ong {
  id: number;
  nome: string;
  description?: string;
}

interface Skill {
  skillName: string;
  volunteerId: string;
  description: string;
  level: string;
}

@Component({
  selector: 'app-dashboard-voluntario',
  standalone: true,
  imports: [CommonModule, FormsModule, DashboardLayoutComponent],
  templateUrl: './dashboard-volunteer.component.html',
  styleUrls: ['./dashboard-volunteer.component.scss']
})
export class DashboardVolunteerComponent {
  events: Event[] = [];
  ongs: Ong[] = [];
  loading = { skill: false };
  skill: Skill = {
    skillName: '',
    volunteerId: '',
    description: '',
    level: ''
  };
  skillCount: number = 0;
  private readonly API_URL = 'http://localhost:9090';

  constructor(private http: HttpClient) {
    this.loadEvents();
    this.loadOngs();
    this.initializeVolunteerId();
    this.loadSkillCount(); // Adicione aqui
  }

  private initializeVolunteerId() {
    // Obter o ID do voluntário do localStorage ou token decodificado
    const volunteerId = this.getCurrentVolunteerId();
    if (volunteerId) {
      this.skill.volunteerId = volunteerId;
    }
  }

  private getCurrentVolunteerId(): string {
    try {
      // Primeira tentativa: volunteerId direto no localStorage
      let volunteerId = localStorage.getItem('volunteerId');
      if (volunteerId) {
        return volunteerId;
      }

      // Segunda tentativa: extrair do token ou dados do usuário
      const authToken = localStorage.getItem('authToken');
      if (authToken) {
        try {
          const parsedAuth = JSON.parse(authToken);
          // Ajuste conforme a estrutura do seu token/resposta de login
          return parsedAuth?.volunteerId || parsedAuth?.userId || parsedAuth?.id || '';
        } catch {
          // Token não é JSON, pode ser JWT - você pode decodificar aqui se necessário
          return '';
        }
      }

      // Terceira tentativa: dados do usuário logado
      const userData = localStorage.getItem('currentUser');
      if (userData) {
        const user = JSON.parse(userData);
        return user?.id || user?.volunteerId || '';
      }

    } catch (error) {
      console.error('Erro ao obter volunteerId:', error);
    }
    return '';
  }

  loadEvents() {
    this.http.get<Event[]>(`${this.API_URL}/events`).subscribe({
      next: (res) => this.events = res,
      error: (error) => {
        console.error('Erro ao carregar eventos:', error);
        this.events = [];
      }
    });
  }

  loadOngs() {
    this.http.get<Ong[]>(`${this.API_URL}/ong`).subscribe({
      next: (res) => this.ongs = res,
      error: (error) => {
        console.error('Erro ao carregar ONGs:', error);
        this.ongs = [];
      }
    });
  }

  loadSkillCount() {
    const volunteerId = this.getCurrentVolunteerId();
    if (!volunteerId) {
      this.skillCount = 0;
      return;
    }
    this.http.get<any[]>(`${this.API_URL}/volunteer-skill/${volunteerId}`).subscribe({
      next: (skills) => {
        this.skillCount = Array.isArray(skills) ? skills.length : 0;
      },
      error: (error) => {
        console.error('Erro ao carregar habilidades:', error);
        this.skillCount = 0;
      }
    });
  }

  inscrever(eventId: number) {
    // Implemente a inscrição conforme sua API
    alert(`Inscrito no evento ${eventId}!`);
  }

  cadastrarSkill() {
    // Validações
    if (!this.skill.skillName || !this.skill.description) {
      alert('Preencha todos os campos obrigatórios');
      return;
    }

    // Garantir que volunteerId está preenchido
    if (!this.skill.volunteerId) {
      const volunteerId = this.getCurrentVolunteerId();
      if (!volunteerId) {
        alert('Erro: Não foi possível identificar o voluntário. Faça login novamente.');
        return;
      }
      this.skill.volunteerId = volunteerId;
    }

    console.log('📤 Dados que serão enviados:', this.skill);

    this.loading.skill = true;

    this.http.post(`${this.API_URL}/volunteer-skill`, this.skill, { responseType: 'text' }).subscribe({
      next: (response) => {
        console.log('✅ Habilidade cadastrada com sucesso:', response);
        alert('Habilidade cadastrada!');
        this.skill = {
          skillName: '',
          volunteerId: this.getCurrentVolunteerId(),
          description: '',
          level: ''
        };
        this.loading.skill = false;
        this.loadSkillCount(); // Atualize o count após cadastrar
      },
      error: (error) => {
        console.error('❌ Erro ao cadastrar habilidade:', error);
        console.error('Status:', error.status);
        console.error('Mensagem:', error.error);

        this.loading.skill = false;

        // Tratamento específico para diferentes erros
        switch (error.status) {
          case 403:
            alert('Acesso negado. Verifique se você está logado e tem permissão para cadastrar habilidades.');
            break;
          case 401:
            alert('Token expirado. Faça login novamente.');
            // Redirecionar para login se necessário
            break;
          case 400:
            alert('Dados inválidos. Verifique os campos preenchidos.');
            break;
          default:
            alert('Erro ao cadastrar habilidade. Tente novamente.');
        }
      }
    });
  }
}
