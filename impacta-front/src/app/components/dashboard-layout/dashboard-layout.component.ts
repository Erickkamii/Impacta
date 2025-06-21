import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-dashboard-layout',
  templateUrl: './dashboard-layout.component.html',
  styleUrls: ['./dashboard-layout.component.scss']
})
export class DashboardLayoutComponent {
  @Input() userLabel: string = '';
  @Input() userIcon: string = '';
  @Input() searchPlaceholder: string = 'Buscar...';
}
