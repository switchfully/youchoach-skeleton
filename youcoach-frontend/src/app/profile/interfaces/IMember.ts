export interface IMember {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  classYear: string;
  youcoachRole: {
    name: string,
    label: string
  };
}
