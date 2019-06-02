import { Component, ViewChild, ViewEncapsulation } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { FooCreateDialogComponent } from 'src/app/secure/shared/components/model/foo/views/create-modal.component';
import { GaeListViewKeyQuerySourceDirective } from '@gae-web/appengine-components';
import { Foo } from 'src/app/secure/shared/api/model/foo/foo';
import { CreateResponse } from '@gae-web/appengine-api';

@Component({
  templateUrl: './list.component.html',
  encapsulation: ViewEncapsulation.None
})
export class ModelListComponent {

  @ViewChild(GaeListViewKeyQuerySourceDirective)
  private readonly _querySource: GaeListViewKeyQuerySourceDirective<Foo>;

  private _ref: MatDialogRef<FooCreateDialogComponent>;

  constructor(private _dialog: MatDialog) { }

  openCreateDialog() {
    if (!this._ref) {
      this._ref = this._dialog.open(FooCreateDialogComponent);

      this._ref.afterClosed().subscribe((result: CreateResponse<Foo> | undefined) => {
        if (result) {
          this._querySource.refresh();
        }
      }).add(() => {
        this._ref = undefined;
      });
    }
  }

}
