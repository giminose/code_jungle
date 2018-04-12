import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { routerTransition } from 'app/router.animations';

@Component({
  selector: 'app-photoresist',
  templateUrl: 'photoresist.component.html',
  styleUrls: ['./photoresist.component.scss'],
  animations: [routerTransition()],
  encapsulation: ViewEncapsulation.None
})

export class PhotoresistComponent implements OnInit {
  fomula = 'y=-0.0002x+0.6739';
  rSquare = 'R-Square=0.9329';
  rawData: Array<any> = [
    {
      x: 450,
      y: 0.5833
    },
    {
      x: 470,
      y: 0.584
    },
    {
      x: 480,
      y: 0.5774
    },
    {
      x: 500,
      y: 0.577
    },
    {
      x: 510,
      y: 0.5723
    },
    {
      x: 510,
      y: 0.575
    },
    {
      x: 520,
      y: 0.566
    },
    {
      x: 540,
      y: 0.5622
    },
    {
      x: 560,
      y: 0.5605
    },
    {
      x: 580,
      y: 0.5568
    }
  ];
  fomulaData: Array<any> = [];
  fomulas: Array<any> = [
    {
      photoresist: 'RH9910',
      cd: this.fomula,
      cieX: this.fomula,
      cieY: this.fomula,
      cieBY: this.fomula,
      spin: this.fomula,
      cdRawData: [

      ],
      cieXRawData: [

      ],
      cieYRawData: [

      ],
      cieBYRawData: [

      ],
      spinRawData: [

      ]
    },
    {
      photoresist: 'RH9920',
      cd: this.fomula,
      cieX: this.fomula,
      cieY: this.fomula,
      cieBY: this.fomula,
      spin: this.fomula,
      cdRawData: [],
      cieXRawData: [],
      cieYRawData: [],
      cieBYRawData: [],
      spinRawData: []
    },
    {
      photoresist: 'RH9930',
      cd: this.fomula,
      cieX: this.fomula,
      cieY: this.fomula,
      cieBY: this.fomula,
      spin: this.fomula,
      cdRawData: [],
      cieXRawData: [],
      cieYRawData: [],
      cieBYRawData: [],
      spinRawData: []
    },
    {
      photoresist: 'RH9940',
      cd: this.fomula,
      cieX: this.fomula,
      cieY: this.fomula,
      cieBY: this.fomula,
      spin: this.fomula,
      cdRawData: [],
      cieXRawData: [],
      cieYRawData: [],
      cieBYRawData: [],
      spinRawData: []
    },
    {
      photoresist: 'RH9950',
      cd: this.fomula,
      cieX: this.fomula,
      cieY: this.fomula,
      cieBY: this.fomula,
      spin: this.fomula,
      cdRawData: [],
      cieXRawData: [],
      cieYRawData: [],
      cieBYRawData: [],
      spinRawData: []
    }
  ];
  chartData = {
    datasets: [
      {
        borderColor: 'rgb(255, 0, 0)',
        fill: false,
        label: '',
        type: 'bubble',
        data: this.rawData
      },
      {
        borderColor: 'rgb(0, 117, 0)',
        fill: false,
        label: '',
        data: this.fomulaData
      }
    ]
  };
  chartOptions = {
    title: {
      display: true,
      text: this.fomula + ", " + this.rSquare
    },
    responsive: true,
    legend: {
        display: false,
        position: 'bottom'
    },
    scales: {
        xAxes: [{
            type: 'linear',
            display: true,
            scaleLabel: {
                display: true,
                labelString: 'RPM'
            }
        }],
        yAxes: [{
            display: true,
            scaleLabel: {
                display: false,
                labelString: 'value'
            }
        }]
    },
    elements: {
        point: {
            radius: 3.5
        },
        line: {
            tension: 0
        }
    },
    tooltips: {
        mode: 'index',
        intersect: false,
    }
  };

  round = function(number, precision) {
    var factor = Math.pow(10, precision);
    var tempNumber = number * factor;
    var roundedTempNumber = Math.round(tempNumber);
    return roundedTempNumber / factor;
  };
  ngOnInit(): void {
    let xSum = 0;
    let xBar = 0;
    let ySum = 0;
    let yBar = 0;
    this.rawData.forEach(e => {
      xSum += e.x;
      ySum += e.y;
    });
    xBar = xSum / this.rawData.length;
    yBar = ySum / this.rawData.length;
    let b = 0;
    let a = 0;
    let aNumerator = 0;
    let aDenominator = 0;
    this.rawData.forEach(e => {
      aNumerator += (e.x - xBar)*(e.y - yBar);
      aDenominator += Math.pow((e.x - xBar), 2);
    })
    a = this.round(aNumerator / aDenominator, 4);
    b = this.round(yBar - a*xBar, 4);
    this.fomula = ("y=" + a + "x" + (b > 0 ? "+" : "") + b);
    let min = this.rawData.reduce((a, b) => {
      return {x:Math.min(a.x, b.x)};
    });
    let max = this.rawData.reduce((a, b) => {
      return {x:Math.max(a.x, b.x)};
    });
    min.y = this.round(a*min.x + b, 4);
    max.y = this.round(a*max.x + b, 4);
    this.fomulaData.push(min, max);

    let sX = 0;
    let sY = 0;
    let xRangeSum = 0;
    let yRangeSum = 0;
    this.rawData.forEach(e => {
      xRangeSum += Math.pow(e.x - xBar, 2);
      yRangeSum += Math.pow(e.y - yBar, 2);
    });
    sX = Math.sqrt(xRangeSum / (this.rawData.length - 1));
    sY = Math.sqrt(yRangeSum / (this.rawData.length - 1));
    let r2 = this.round(Math.pow(aNumerator / ((this.rawData.length - 1) * sX * sY), 2), 4);
  }
}