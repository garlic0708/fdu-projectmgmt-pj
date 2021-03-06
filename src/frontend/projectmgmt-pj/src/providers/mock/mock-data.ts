import range from 'lodash/range';
import { Poi } from "../../components/amap/poi";

const detail: { [p: string]: any, address: Poi } = {
  'id': 1,
  'name': 'Name of the event',
  'tags': [
    { name: 'tag1' },
    { name: 'tag2' },
  ],
  'description': 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.',
  'image': './assets/imgs/placeholder.png',
  'initiator': {
    name: 'Initiator of this event',
    avatar: './assets/imgs/placeholder.png',
  },
  'startTime': '2018-12-15T18:00:00.000+08:00',
  'endTime': '2018-12-15T20:00:00.000+08:00',
  'address': {
    id: '',
    name: 'Name of the address',
    location: { lng: 121.5, lat: 31.5 },
    address: 'Address of the address',
  },
  'lowerBound': 5,
  'upperBound': 6,
  'currentParticipants': 6,
  'status': 'notStarted',
  'joined': false,
};
export const mockData: any = {
  '#GET /api/event/detail/:id': detail,
  '#GET /api/event/home-slides': [
    { path: './assets/imgs/logo.png', title: 'Title 1', id: 1 },
    { path: './assets/imgs/placeholder.png', title: 'Title 2', id: 2 },
    { path: './assets/imgs/placeholder.png', title: 'Title 3', id: 3 },
  ],
  '#GET /api/event/home-flow': [
    { path: './assets/imgs/placeholder.png', title: 'Title 1', id: 1 },
    { path: './assets/imgs/placeholder.png', title: 'Title 2', id: 2 },
    { path: './assets/imgs/placeholder.png', title: 'Title 3', id: 3 },
    { path: './assets/imgs/placeholder.png', title: 'Title 4, This title seems to be longer', id: 4 },
    { path: './assets/imgs/placeholder.png', title: 'Title 5', id: 5 },
    // { path: './assets/imgs/placeholder.png', title: 'Title 6', id: 6 },
  ],
  'GET /api/event-tag/list': range(5).map(n => {
    return { id: n + 1, name: `tag ${n + 1}` }
  }),
  '#GET /api/notif/notif-list': [
    { type: 'unread', content: 'Content 1', id: 1 },
    { type: 'unread', content: 'Content 2', id: 2 },
    {
      type: 'unread',
      content: 'Content 3, testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest',
      id: 3
    },
  ],
  'GET /api/personal/events-joined': [
    { path: './assets/imgs/placeholder.png', title: 'Title 5', id: 5, status: 'canceled' },
    { path: './assets/imgs/logo.png', title: 'Title 1', id: 1, status: 'notstarted' },
    { path: './assets/imgs/placeholder.png', title: 'Title 2', id: 2, status: 'notstarted' },
    { path: './assets/imgs/placeholder.png', title: 'Title 3', id: 3, status: 'started' },
    { path: './assets/imgs/placeholder.png', title: 'Title 6', id: 3, status: 'started' },
    { path: './assets/imgs/placeholder.png', title: 'Title 4', id: 4, status: 'ended' },
  ],
  'GET /api/personal/events-released': [
    { path: './assets/imgs/placeholder.png', title: 'Title 5', id: 5, status: 'canceled' },
    { path: './assets/imgs/logo.png', title: 'Title 1', id: 1, status: 'notstarted' },
    { path: './assets/imgs/placeholder.png', title: 'Title 2', id: 2, status: 'notstarted' },
    { path: './assets/imgs/placeholder.png', title: 'Title 3', id: 3, status: 'started' },
    { path: './assets/imgs/placeholder.png', title: 'Title 6', id: 3, status: 'started' },
    { path: './assets/imgs/placeholder.png', title: 'Title 4', id: 4, status: 'ended' },
  ],
  '#GET /api/event/checkin/:id': [
    { id: 0, name: 'student1', type: 0 },
    { id: 1, name: 'student2', type: 1 },
    { id: 2, name: 'student3', type: 0 },
    { id: 3, name: 'student4', type: 1 },
    { id: 4, name: 'student5', type: 0 },
    { id: 5, name: 'student6', type: 0 },
  ],
  'GET /api/nearby/list': [
    { id: 1, name: 'Event 1', x: 121.5940, y: 31.1900 },
    { id: 2, name: 'Event 2', x: 121.5942, y: 31.1901 },
    { id: 3, name: 'Event 3', x: 121.5944, y: 31.1902 }
  ]
};
