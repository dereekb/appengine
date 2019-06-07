import { Component, ViewChild, ViewEncapsulation } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { FooCreateDialogComponent } from 'src/app/secure/shared/components/model/foo/views/create-modal.component';
import { GaeListViewKeyQuerySourceDirective } from '@gae-web/appengine-components';
import { Foo } from 'src/app/secure/shared/api/model/foo/foo';
import { CreateResponse } from '@gae-web/appengine-api';
import { FooCreateActionDirective } from 'src/app/secure/shared/components/model/foo/client/create.directive';
import { MatProgressButtonOptions } from 'mat-progress-buttons';

@Component({
  templateUrl: './list.component.html',
  encapsulation: ViewEncapsulation.None
})
export class ModelListComponent {

  @ViewChild(GaeListViewKeyQuerySourceDirective, { static: true })
  private readonly _querySource: GaeListViewKeyQuerySourceDirective<Foo>;

  @ViewChild(FooCreateActionDirective, { static: true })
  private readonly _createAction: FooCreateActionDirective;

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

  createJunk() {
    this._createAction.testCreate(20).subscribe(() => {
      this._querySource.refresh();
    });
  }

  get createJunkButtonOptions(): MatProgressButtonOptions {
    return {
      active: this._createAction.isWorking,
      text: 'Create 20 Items',
      buttonColor: 'accent',
      barColor: 'accent',
      raised: true,
      mode: 'indeterminate'
    };
  }

}
