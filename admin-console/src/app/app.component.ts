import { Component } from '@angular/core';
import { WishItem } from 'src/shared/models/wishitem';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})

export class AppComponent {

  newWishText = '';

  items : WishItem[] = [
    new WishItem('To Learn Angular'),
    new WishItem('Get Coffe',true),
    new WishItem('Find Glass that cuts itself')
  ]

  filter : any = () => {};
    
  get visibleItems() : WishItem[] {
    return this.items.filter(this.filter);
  }
}
