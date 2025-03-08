import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-default-signup-layout',
  imports: [],
  templateUrl: './default-signup-layout.component.html',
  styleUrl: './default-signup-layout.component.scss'
})
export class DefaultSignupLayoutComponent {
  @Input() title: string = "";
  @Input() changeBtnTxt: string = "";
  @Input() primaryBtnTxt: string = "";
  @Input() secondaryBtnTxt: string = "";
  @Input() disablePrimaryBtn: boolean = true;
  @Output("submit") onSubmit = new EventEmitter();
  @Output("navigate") onNavigate = new EventEmitter();
  @Output("navigateSign") onNavigateSign = new EventEmitter();

  submit(){
    this.onSubmit.emit();
  }

  navigate(){
    this.onNavigate.emit();
  }

  navigateSign(){
    this.onNavigateSign.emit();
  }
}
