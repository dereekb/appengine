import { Component } from '@angular/core';
import { AbstractModelViewComponent } from './view.component';
import { MatDialogRef, MatDialog } from '@angular/material/dialog';
import { FooDeleteDialogComponent } from '../../../../shared/components/model/foo/views/delete-modal.component';

@Component({
  templateUrl: './info.component.html',
})
export class ModelInfoViewComponent extends AbstractModelViewComponent {

  private _ref: MatDialogRef<FooDeleteDialogComponent>;

  constructor(private _dialog: MatDialog) {
    super();
  }

  openDeleteDialog() {
    if (!this._ref) {
      this._ref = this._dialog.open(FooDeleteDialogComponent);
      this._ref.afterClosed().subscribe(() => {
        this._ref = undefined;
      });

      const deleteComponent = this._ref.componentInstance;

      deleteComponent.targetKey = this.fooKey;
      deleteComponent.segueRef = '^.^';
    }
  }

}
