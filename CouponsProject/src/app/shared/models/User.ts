import { UserLoginDetails } from './UserLoginDetails';

export class User {
    public constructor( public userId?:number,
                        public companyId?:number,
                        public userLoginDetails?:UserLoginDetails
                        )
        {

        }
}