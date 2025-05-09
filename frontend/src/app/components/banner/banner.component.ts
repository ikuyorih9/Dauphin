import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-banner',
  imports: [BannerComponent],
  templateUrl: './banner.component.html',
  styleUrl: './banner.component.css'
})
export class BannerComponent {
  @Input() headerText: string = 'header';
  @Input() bannerWidth: string = '50vw';
  @Input() bannerHeight: string = '50vh';
}
