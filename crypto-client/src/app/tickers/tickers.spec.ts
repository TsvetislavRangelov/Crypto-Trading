import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Tickers } from './tickers';

describe('Tickers', () => {
  let component: Tickers;
  let fixture: ComponentFixture<Tickers>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Tickers]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Tickers);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
