import { Component, Output, EventEmitter } from '@angular/core';
import { WishItem } from 'src/shared/models/wishitem';

@Component({
  selector: 'add-wish-form',
  templateUrl: './add-wish-form.component.html',
  styleUrls: ['./add-wish-form.component.scss']
})
export class AddWishFormComponent {

  @Output() addWish = new EventEmitter<WishItem>();
  

  newWishText = '';

  addNewWish() {
    this.addWish.emit(new WishItem(this.newWishText))
    this.newWishText = '';
  }
}
