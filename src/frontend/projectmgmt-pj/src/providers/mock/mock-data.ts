import range from 'lodash/range';

export const mockData: any = {
  'GET /api/event/detail/:id': {
    'id': 1,
    'name': 'Name of the event',
    'types': [
      { name: 'type1' },
      { name: 'type2' },
    ],
    'description': 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.',
    'image': './assets/imgs/placeholder.png',
    'initiator': {
      name: 'Initiator of this event',
      avatar: './assets/imgs/placeholder.png',
    },
    'startTime': '2018-12-15T18:00:00.000+08:00',
    'endTime': '2018-12-15T20:00:00.000+08:00',
    'address': '1000 Some road',
    'lowerBound': 5,
    'upperBound': 14,
    'currentAttendants': 6,
  },
  'GET /api/event/home-slides': [
    { path: './assets/imgs/logo.png', title: 'Title 1', id: 1 },
    { path: './assets/imgs/placeholder.png', title: 'Title 2', id: 2 },
    { path: './assets/imgs/placeholder.png', title: 'Title 3', id: 3 },
  ],
  'GET /api/event/home-flow': [
    { path: './assets/imgs/placeholder.png', title: 'Title 1', id: 1 },
    { path: './assets/imgs/placeholder.png', title: 'Title 2', id: 2 },
    { path: './assets/imgs/placeholder.png', title: 'Title 3', id: 3 },
    { path: './assets/imgs/placeholder.png', title: 'Title 4, This title seems to be longer', id: 4 },
    { path: './assets/imgs/placeholder.png', title: 'Title 5', id: 5 },
    // { path: './assets/imgs/placeholder.png', title: 'Title 6', id: 6 },
  ],
  'GET /api/event-type/list': range(5).map(n => {
    return { id: n + 1, name: `Type ${n + 1}` }
  }),
  'GET /api/notif/notif-list': [
    { type: 1, title:'Title 1', content: 'Content 1', id: 1 },
    { type: 2, title:'Title 2', content: 'Content 2', id: 2 },
    { type: 3, title:'Title 3, testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest', content: 'Content 3, testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest', id: 3 },
  ],
  'GET /api/notif/notif-detail/:id': {
    'type': 1,
    'title': 'Title 1',
    'content': 'Content 1',
  },
  'GET /api/personal/events-joined': [
    { path: './assets/imgs/placeholder.png', title: 'Title 5', id: 5, status: 'canceled'},
    { path: './assets/imgs/logo.png', title: 'Title 1', id: 1 , status: 'notstarted'},
    { path: './assets/imgs/placeholder.png', title: 'Title 2', id: 2, status: 'notstarted'},
    { path: './assets/imgs/placeholder.png', title: 'Title 3', id: 3, status: 'started'},
    { path: './assets/imgs/placeholder.png', title: 'Title 6', id: 3, status: 'started'},
    { path: './assets/imgs/placeholder.png', title: 'Title 4', id: 4, status: 'ended'},
  ],
  //'GET /api/personal/events-released': [
  //  { path: './assets/imgs/logo.png', title: 'Title 1', id: 1 },
  //  { path: './assets/imgs/placeholder.png', title: 'Title 2', id: 2 },
  //  { path: './assets/imgs/placeholder.png', title: 'Title 3', id: 3 },
  //  { path: './assets/imgs/placeholder.png', title: 'Title 4', id: 4 },
  //  { path: './assets/imgs/placeholder.png', title: 'Title 5', id: 5 },
  //  { path: './assets/imgs/placeholder.png', title: 'Title 6', id: 6 },
  //],
  'GET /api/event/checkin/:id': {
    'person': [
      {id: 0, name: 'student1', type: 0},
      {id: 1, name: 'student2', type: 1},
      {id: 2, name: 'student3', type: 0},
      {id: 3, name: 'student4', type: 1},
      {id: 4, name: 'student5', type: 0},
      {id: 5, name: 'student6', type: 0},
    ]
  }
};
