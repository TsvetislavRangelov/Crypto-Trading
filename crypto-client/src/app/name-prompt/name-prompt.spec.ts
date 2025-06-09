import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NamePromptComponent } from './name-prompt';

describe('NamePrompt', () => {
  let component: NamePromptComponent;
  let fixture: ComponentFixture<NamePromptComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NamePromptComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NamePromptComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
