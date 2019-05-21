import { Component, ViewChild } from '@angular/core';
import { MatDialogRef } from '@angular/material';
import { FooCreateFormComponent } from './create-form.component';
import { FooCreateActionDirective } from '../client/create.directive';
import { AbstractCreateActionDialogCompoment } from '@gae-web/appengine-components';
import { TestFoo } from 'projects/gae-web/appengine-api/src/public-api';

@Component({
  templateUrl: 'create-modal.component.html',
})
export class FooCreateDialogComponent extends AbstractCreateActionDialogCompoment<TestFoo> {}
