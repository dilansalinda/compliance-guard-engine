import { Component, Input } from '@angular/core';
import { WishItem } from 'src/shared/models/wishitem';

@Component({
  selector: 'wish-list',
  templateUrl: './wish-list.component.html',
  styleUrls: ['./wish-list.component.scss']
})
export class WishListComponent {

  @Input() wishes : WishItem[] = [];

  toggleItem (item : WishItem) {
    item.isComplete=!item.isComplete;
    console.log("click", item);
  }
}
