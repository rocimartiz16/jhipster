import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IFichaTrimestre, defaultValue } from 'app/shared/model/ficha-trimestre.model';

export const ACTION_TYPES = {
  FETCH_FICHATRIMESTRE_LIST: 'fichaTrimestre/FETCH_FICHATRIMESTRE_LIST',
  FETCH_FICHATRIMESTRE: 'fichaTrimestre/FETCH_FICHATRIMESTRE',
  CREATE_FICHATRIMESTRE: 'fichaTrimestre/CREATE_FICHATRIMESTRE',
  UPDATE_FICHATRIMESTRE: 'fichaTrimestre/UPDATE_FICHATRIMESTRE',
  DELETE_FICHATRIMESTRE: 'fichaTrimestre/DELETE_FICHATRIMESTRE',
  RESET: 'fichaTrimestre/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IFichaTrimestre>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type FichaTrimestreState = Readonly<typeof initialState>;

// Reducer

export default (state: FichaTrimestreState = initialState, action): FichaTrimestreState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_FICHATRIMESTRE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_FICHATRIMESTRE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_FICHATRIMESTRE):
    case REQUEST(ACTION_TYPES.UPDATE_FICHATRIMESTRE):
    case REQUEST(ACTION_TYPES.DELETE_FICHATRIMESTRE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_FICHATRIMESTRE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_FICHATRIMESTRE):
    case FAILURE(ACTION_TYPES.CREATE_FICHATRIMESTRE):
    case FAILURE(ACTION_TYPES.UPDATE_FICHATRIMESTRE):
    case FAILURE(ACTION_TYPES.DELETE_FICHATRIMESTRE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_FICHATRIMESTRE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_FICHATRIMESTRE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_FICHATRIMESTRE):
    case SUCCESS(ACTION_TYPES.UPDATE_FICHATRIMESTRE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_FICHATRIMESTRE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/ficha-trimestres';

// Actions

export const getEntities: ICrudGetAllAction<IFichaTrimestre> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_FICHATRIMESTRE_LIST,
  payload: axios.get<IFichaTrimestre>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IFichaTrimestre> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_FICHATRIMESTRE,
    payload: axios.get<IFichaTrimestre>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IFichaTrimestre> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_FICHATRIMESTRE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IFichaTrimestre> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_FICHATRIMESTRE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IFichaTrimestre> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_FICHATRIMESTRE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
